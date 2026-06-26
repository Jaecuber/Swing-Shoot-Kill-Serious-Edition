<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="objects" tilewidth="25" tileheight="22" tilecount="4" columns="0">
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
 <tile id="3" type="Prop">
  <properties>
   <property name="acceleration" type="float" value="10"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="damage" type="float" value="5"/>
   <property name="maxSpinSpeed" type="float" value="1000"/>
   <property name="name" value="playersword"/>
   <property name="stamConsume" type="float" value="10"/>
   <property name="stamRegen" type="float" value="5"/>
  </properties>
  <image source="Objects/playerSword.png" width="22" height="22"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="melee_sensor" x="7.63674" y="10.2727">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <polygon points="0,0 3.54508,3.78167 -10.7076,18.4545 -13.8186,14.7485"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="4" type="Prop">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="canShoot" type="bool" value="true"/>
   <property name="capacity" type="int" value="6"/>
   <property name="cooldown" type="float" value="0.5"/>
   <property name="damage" type="float" value="1"/>
   <property name="name" value="playergun"/>
   <property name="reloadTime" type="float" value="2"/>
   <property name="trackRotation" type="bool" value="true"/>
  </properties>
  <image source="Objects/playerRevolver.png" width="25" height="11"/>
 </tile>
 <tile id="5" type="GameObject">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="lifetime" type="float" value="1"/>
   <property name="name" value="revolver_Bullet"/>
   <property name="projectile" type="bool" value="true"/>
   <property name="speed" type="float" value="15"/>
  </properties>
  <image source="Objects/bullet.png" width="4" height="4"/>
  <objectgroup draworder="index" id="3">
   <object id="4" name="bullet_sensor" x="-1.12326" y="-1.22604" width="6.2316" height="6.32361">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <capsule/>
   </object>
  </objectgroup>
 </tile>
</tileset>
