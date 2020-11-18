###
Copyright 2020 Malte Dostal

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
###

key-value syntax:
    .key=value
    where key is the name of the key
    and value the string of following characters
    to be associated with the key. The name of the
    key ending with [] indicates a list of values
    where entries are separated by a comma (,).
    examples:
        .name=Malte Dostal
        .highscore-history[]=271,314,981
class-affiliation is based on indentation.
Indentation-width may be specified in the meta class.
List items are separated by a , by default or
by :meta.list_separator
:meta
    .author=Malte Dostal
    .name=format
    .indentation=4
    the string specified by the following "newline" key
    is replaced by an actual newline when the file is
    being parsed to allow for multiline values
    .newline=\n
    .list_suffix=[]
    .list_separator=,
The following "real-world" example
is in the context that this format is specifically
being developed for the use with Salty Engine, a game engine
:save0
    :player
        .princess_saved=true
        .coins_collected=3141
        .gravity=9.81
        :items
            .skins[]=little prince,big orc,giant king
    :settings
        .fullscreen=false
        .volume=42
