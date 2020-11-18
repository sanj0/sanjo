parsed line by line:
line starts with:
    : -> class
    . -> key-value pair
    anything else -> ignored
key-value syntax:
    .key=value
    where key is the name of the key
    and value the string of following characters
    to be associated with the key. The name of the
    key ending with [] indicates an array of values
    where entries are separated by a comma (,).
    examples:
        .name=Malte Dostal
        .highscore-history[]=271,314,981
class-affiliation is based on indentation.
Indentation-width may be specified in the meta class.
List items are separated by a , by default or
by :meta.list_separator
:meta
    .indentation=4
    .newline=\n
    .author=Malte Dostal
    .name=format
    .list_suffix=[]
    .list_separator=,
The following "real-world" example
is in the context that this format is specifically
being developed for the use with Salty Engine, a game engine
:save0
    :player1
        .princess_saved=true
        .coins_collected=3141
        .gravity=9.81
        :class1
            .skins[]=little prince,big orc,giant king
    :settings
        .fullscreen=false
        .volume=69
