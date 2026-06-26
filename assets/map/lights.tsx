<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="lights" tilewidth="16" tileheight="16" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="1">
  <properties>
   <property name="lightColor" type="color" value="#64ff9500"/>
   <property name="lightDistance" type="float" value="5"/>
  </properties>
  <image source="objects/light.png" width="16" height="16"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0.125" y="0.125" width="15.875" height="15.75">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
