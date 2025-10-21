/**
 *  Aurora Borealis Lighting
 *
 *  Copyright 2025
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

definition(
    name: "Aurora Borealis Lighting",
    namespace: "custom",
    author: "Colin Ho",
    description: "Simulates Northern Lights by cycling colors on selected color bulbs.",
    category: "Lighting",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "")

preferences {
    section("Select color bulbs for aurora effect") {
        input "colorBulbs", "capability.colorControl", title: "Color Bulbs", multiple: true, required: true
    }
    section("Color change speed (seconds)") {
        input "speed", "number", title: "Transition speed", required: true, defaultValue: 3
    }
}

def installed() {
    initialize()
}

def updated() {
    unschedule()
    initialize()
}

def initialize() {
    runIn(2, auroraLoop)
}

def auroraLoop() {
    if (!colorBulbs) return // nothing to do

    def speedMs = (settings.speed ?: 3) * 1000
    def bulbs = colorBulbs
    
    // Turn off all color bulbs NOT in the selected list
    def allColorBulbs = location.allDevices.findAll { it.hasCapability("ColorControl") }
    def unselectedBulbs = allColorBulbs - bulbs
    unselectedBulbs.each { it.off() }
    
    // Create a harmonious base hue for this cycle (aurora colors: green-blue range)
    def baseHue = 50 + new Random().nextInt(40) // Range 50-90 (green to cyan/blue)
    def baseSaturation = 85 + new Random().nextInt(15) // Range 85-100
    
    def randomOrder = bulbs.sort{new Random().nextInt()}
    randomOrder.eachWithIndex { bulb, idx ->
        // Create harmonious offset for each bulb (small variation from base)
        def hueOffset = (idx % 3 - 1) * (5 + new Random().nextInt(5)) // -10 to +10 degree variation
        def harmonicHue = (baseHue + hueOffset) % 100
        def harmonicSaturation = baseSaturation - new Random().nextInt(10) // Slight sat variation
        
        bulb.setColor([hue: harmonicHue, saturation: harmonicSaturation, level: 100])
        pauseExecution((speedMs / bulbs.size()).toInteger())
    }
    runIn(settings.speed ?: 3, auroraLoop)
}
