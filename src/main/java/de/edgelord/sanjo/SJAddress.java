/*
 * Copyright 2020 Malte Dostal
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.edgelord.sanjo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SJAddress {

    private final String address;
    private final List<AddressComponent> addressComponents;

    public SJAddress(String address) {
        this.address = address;
        addressComponents = new ArrayList<>();
        evaluateAddress();
    }

    public static SJAddress forString(final String address) {
        return new SJAddress(address);
    }

    public Target getTargetType() {
        return addressComponents.get(addressComponents.size() - 1).targetType;
    }

    private void evaluateAddress() {
        final char[] addressChars = address.toCharArray();
        StringBuilder currentData = new StringBuilder();
        Target currentTarget = null;

        for (final char c : addressChars) {
            if (c == SanjoParser.CLASS_PREFIX) {
                currentData = addAddressComponent(currentData, currentTarget);
                currentTarget = Target.CLASS;
            } else if (c == SanjoParser.KEY_PREFIX) {
                currentData = addAddressComponent(currentData, currentTarget);
                currentTarget = Target.VALUE;
            } else {
                currentData.append(c);
            }
        }
        addAddressComponent(currentData, currentTarget);
    }

    private StringBuilder addAddressComponent(final StringBuilder builder, final Target target) {
        if (builder.length() != 0) {
            addressComponents.add(new AddressComponent(builder.toString(), target));
            return new StringBuilder();
        } else {
            // doesn't really matter as the
            // string builder is empty anyway
            return builder;
        }
    }

    /**
     * Creates every subclass so that
     * this address' target exists with the given
     * {@link SJClass class} as the entry point.
     * <p>In case this address points to a
     * {@link SJValue value}, an {@link SJValue.Empty empty value}
     * is added in the according (maybe just generated)
     * {@link SJClass class}.
     *
     * @return the target of this address
     */
    public Object create(final SJClass root) {
        return walk(root, (c, ac) -> {
            if (ac.targetType == Target.CLASS) {
                if (c.getChild(ac.target) instanceof SJClass.Empty) {
                    c.getChildren().add(new SJClass(ac.target, c, c.getMetaInf()));
                }
            } else if (c.getValue(ac.target) instanceof SJValue.Empty) {
                c.getValues().put(ac.target, new SJValue(ac.target, ""));
            }
        });
    }

    //TODO: rename (to "find"?)
    public Object apply(final SJClass root) {
        return walk(root, (c, ac) -> {});
    }

    private Object walk(final SJClass root, final BiConsumer<SJClass, AddressComponent> preVisitAction) {
        SJClass targetClass = root;
        for (final AddressComponent addressComponent : addressComponents) {
            if (addressComponent.targetType == Target.CLASS) {
                preVisitAction.accept(targetClass, addressComponent);
                targetClass = targetClass.getChild(addressComponent.target);
            } else {
                preVisitAction.accept(targetClass, addressComponent);
                return targetClass.getValue(addressComponent.target);
            }
        }

        return targetClass;
    }

    public enum Target {
        VALUE,
        CLASS
    }

    private static class AddressComponent {
        private final String target;
        private final Target targetType;

        public AddressComponent(final String target, final Target targetType) {
            this.target = target;
            this.targetType = targetType;
        }

        public String getTarget() {
            return target;
        }

        public Target getTargetType() {
            return targetType;
        }
    }
}
