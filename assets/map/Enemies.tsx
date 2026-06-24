<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Enemies" class="Enemy" tilewidth="20" tileheight="20" tilecount="3" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="1"/>
   <property name="name" value="red_cube"/>
   <property name="speed" type="float" value="3"/>
   <property name="type" value="basic"/>
  </properties>
  <image source="objects/redCube.png" width="20" height="20"/>
 </tile>
 <tile id="1" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="2"/>
   <property name="name" value="orange_circle"/>
   <property name="speed" type="float" value="1.5"/>
   <property name="type" value="basic"/>
  </properties>
  <image source="objects/orangeCircle.png" width="20" height="20"/>
 </tile>
 <tile id="2" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="1"/>
   <property name="name" value="yellow_triangle"/>
   <property name="speed" type="float" value="6.7"/>
   <property name="type" value="basic"/>
  </properties>
  <image source="objects/yellowTriangle.png" width="20" height="20"/>
 </tile>
</tileset>
