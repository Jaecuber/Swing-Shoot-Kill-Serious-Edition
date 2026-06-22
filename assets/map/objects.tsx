<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="objects" tilewidth="22" tileheight="22" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="2" type="GameObject">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="health" type="float" value="5"/>
   <property name="speed" type="float" value="5"/>
   <property name="stamina" type="float" value="5"/>
  </properties>
  <image source="Objects/player.png" width="22" height="22"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="collision" x="4.1601" y="14.4217" width="12.6652" height="5.03834">
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
