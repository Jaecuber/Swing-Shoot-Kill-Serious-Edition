<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="player" tilewidth="22" tileheight="22" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="health" type="float" value="5"/>
   <property name="speed" type="float" value="5"/>
   <property name="stamina" type="float" value="100"/>
  </properties>
  <image source="objects/player.png" width="22" height="22"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="1.75" y="16" width="18.5" height="4.375">
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
