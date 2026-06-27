<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Enemies" class="Enemy" tilewidth="32" tileheight="32" tilecount="7" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="1"/>
   <property name="lightColor" type="color" value="#64ff2f2f"/>
   <property name="lightDistance" type="float" value="7.5"/>
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
   <property name="lightColor" type="color" value="#64ff8c00"/>
   <property name="lightDistance" type="float" value="7.5"/>
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
   <property name="lightColor" type="color" value="#64fff700"/>
   <property name="lightDistance" type="float" value="7.5"/>
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
 <tile id="3" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="2"/>
   <property name="lightColor" type="color" value="#640090ff"/>
   <property name="lightDistance" type="float" value="7.5"/>
   <property name="name" value="blue_Rhombus"/>
   <property name="speed" type="float" value="4.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="20"/>
  </properties>
  <image source="objects/blueRhombus.png" width="20" height="20"/>
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
 <tile id="4" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="2"/>
   <property name="health" type="float" value="10"/>
   <property name="lightColor" type="color" value="#64b50000"/>
   <property name="lightDistance" type="float" value="10"/>
   <property name="name" value="giant_Red_Cube"/>
   <property name="speed" type="float" value="1.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="150"/>
  </properties>
  <image source="objects/giantRedCube.png" width="32" height="32"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="2.26705" y="26.1136" width="28.25" height="4.5">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="hitbox" x="4.05682" y="4.47159" width="23.517" height="22.6023">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="attackHitbox" x="4.375" y="4.35795" width="23.25" height="23.4943">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="4" name="attackRadius" x="0.373105" y="0.865531" width="31.0569" height="30.0341">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="detectionRadius" x="-130" y="-131.5" width="300" height="300">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="5" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="2"/>
   <property name="lightColor" type="color" value="#646aff76"/>
   <property name="lightDistance" type="float" value="7.5"/>
   <property name="name" value="green_Trapezoid"/>
   <property name="speed" type="float" value="3"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="25"/>
  </properties>
  <image source="objects/greenTrapezoid.png" width="20" height="20"/>
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
 <tile id="6" type="Enemy">
  <properties>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="1"/>
   <property name="health" type="float" value="4"/>
   <property name="lightColor" type="color" value="#648800ff"/>
   <property name="lightDistance" type="float" value="7.5"/>
   <property name="name" value="purple_Hexagon"/>
   <property name="speed" type="float" value="2"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="basic"/>
   <property name="value" type="int" value="25"/>
  </properties>
  <image source="objects/purpleHexagon.png" width="20" height="20"/>
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
