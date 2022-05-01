> ### Dynamic Surroundings
A Minecraft Mod that alters the fabric of Minecraft experience by weaving a tapestry of sound and visual effects.

This is an Architectury port that supports both Fabric and Forge, adapted from [this](https://github.com/ThexXTURBOXx/DynamicSurroundingsFabric)
repository.

Removed features:
* **Version checker**: will not be added back as not many mods implement them, and there are mods 
available that
allow for update checks on mods that you have installed.
* **Commands**: may be implemented again in the future, however I am not sure of any way to 
(easily) implement client-side commands in Forge, as Architectury does not provide any API to do so.

**Requirements**
* Java 16+
* [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
* 100% client side; no server side deployment needed

**Recommended Additions**
* **Fabric**: [ModMenu](https://modrinth.com/mod/modmenu) to get an in-game mod configuration menu.
* Presence Footsteps
  * [Fabric](https://modrinth.com/mod/presence-footsteps)
  * [Forge](https://github.com/SpatialParadox/Presence-Footsteps)

**Features**
* Enhanced Sound processing - performs calculations in the background, adding a reverb effect to spacial sounds.
* Individual Sound Control - Set key bind and activate in-game.  Use this feature to block, cull, and control the volume at which sounds play.  And as a bonus you can play the sound to hear it.
* Biome sounds - Various atmospheric sounds that play based on biomes in the area.  Seamless blending of sounds as the player moves throughout the world.
    * This does not replace the Minecraft feature of a singular biome background sound.  Currently, the various biomes in the Nether use this capability, and Dynamic Surroundings does not have sound configurations for that dimension.
* Hot block effects such as flame jets over lava, and steam where water hits a hot block.
    * Hot blocks are things like Lava and Magma.
* Waterfall sound and visual effect - will trigger when flowing water is detected nearby.
* Replace Minecraft's thunder sound with improved versions.
* Custom debug HUD that can be accessed by key bind.  Moves the Dynamic Surroundings clutter out of the traditional F3 display.

**Embedded Jars**
* OpenJDK Nashorn JavaScript Engine (https://github.com/openjdk/nashorn)

**FAQ**
* Will there be releases for earlier versions of Minecraft?
    * No.

**What's Being Dropped**
* Aurora.  Good at turning a computer into a space heater, and I do not know enough about shaders to improve.
* Mob/player footstep and toolbar effects. See [Presence Footsteps](https://www.curseforge.com/minecraft/mc-mods/presence-footsteps) for an alternative.
* Specialized fog effects.  Minecraft has made some improvements in this area, and I expect it to continue.  I don't want to be in a position of overriding (or managing the problem) of when Microsoft adds more dynamic content.
* Weather effects. Again, there have been improvements to Minecraft. I may add some additional processing around weather, but I do not expect to make major changes.

As I indicated these features are not planned. Based on time commitments I may change my mind. :) 

**Planned Features**
* Making the config system publicly available so that pack authors can configure things.  This is possible with this release, but I may change things.  Besides, it has to be documented so that someone would know what to do.

> [LICENSE](LICENSE)