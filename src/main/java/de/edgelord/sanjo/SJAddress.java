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
            return builder;
        }
    }

    public Object apply(final SJClass root) {
        SJClass targetClass = root;
        for (final AddressComponent addressComponent : addressComponents) {
            if (addressComponent.targetType == Target.CLASS) {
                targetClass = targetClass.getChild(addressComponent.target);
            } else {
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

        public Object getTarget() {
            return target;
        }

        public Target getTargetType() {
            return targetType;
        }
    }
}
