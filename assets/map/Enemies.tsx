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
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="5"/>
  </properties>
  <image source="objects/redCube.png" width="20" height="20"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="2.8125" y="14.25" width="14.25" height="4.5">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="hitbox" x="3.875" y="4.0625" width="12.0625" height="11.875">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="attackHitbox" x="3.875" y="3.9375" width="12.25" height="12.0625">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="4" name="attackRadius" x="1.82765" y="2.45644" width="16.5114" height="15.6705">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="detectionRadius" x="-86.5" y="-88" width="200" height="200">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="2"/>
   <property name="name" value="orange_circle"/>
   <property name="speed" type="float" value="1.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="10"/>
  </properties>
  <image source="objects/orangeCircle.png" width="20" height="20"/>
  <objectgroup draworder="index" id="3">
   <object id="6" x="2.8125" y="14.25" width="14.25" height="4.5">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="7" name="hitbox" x="3.875" y="4.0625" width="12.0625" height="11.875">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="8" name="attackHitbox" x="3.875" y="3.9375" width="12.25" height="12.0625">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="9" name="attackRadius" x="1.82765" y="2.45644" width="16.5114" height="15.6705">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="detectionRadius" x="-86.5" y="-88" width="200" height="200">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="2" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="1"/>
   <property name="name" value="yellow_triangle"/>
   <property name="speed" type="float" value="5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="10"/>
  </properties>
  <image source="objects/yellowTriangle.png" width="20" height="20"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="2.8125" y="14.25" width="14.25" height="4.5">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="hitbox" x="3.875" y="4.0625" width="12.0625" height="11.875">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="attackHitbox" x="3.875" y="3.9375" width="12.25" height="12.0625">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="4" name="attackRadius" x="1.82765" y="2.45644" width="16.5114" height="15.6705">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="detectionRadius" x="-86.5" y="-88" width="200" height="200">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
