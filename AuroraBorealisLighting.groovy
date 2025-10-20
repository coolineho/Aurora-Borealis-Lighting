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
    author: "Your Name",
    description: "Cycles lights through harmonious aurora borealis-inspired colors with smooth transitions",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: ""
)

preferences {
    page(name: "mainPage")
}

def mainPage() {
    dynamicPage(name: "mainPage", title: "Aurora Borealis Lighting Setup", install: true, uninstall: true) {
        section("Select Lights") {
            input "lights", "capability.colorControl", title: "Which color lights?", multiple: true, required: true
        }
        
        section("Timing Settings") {
            input "transitionTime", "number", title: "Transition time between colors (seconds)", defaultValue: 30, required: true
            input "colorHoldTime", "number", title: "Time to hold each color (seconds)", defaultValue: 10, required: true
        }
        
        section("Brightness") {
            input "brightness", "number", title: "Brightness level (1-100)", defaultValue: 75, range: "1..100", required: true
        }
        
        section("Control") {
            input "enableSwitch", "capability.switch", title: "Enable/disable with this switch (optional)", required: false
        }
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    unschedule()
    initialize()
}

def initialize() {
    if (enableSwitch) {
        subscribe(enableSwitch, "switch", switchHandler)
        if (enableSwitch.currentValue("switch") == "on") {
            startCycle()
        }
    } else {
        startCycle()
    }
}

def switchHandler(evt) {
    if (evt.value == "on") {
        startCycle()
    } else {
        stopCycle()
    }
}

def startCycle() {
    log.debug "Starting Aurora Borealis cycle"
    state.currentColorIndex = 0
    cycleToNextColor()
}

def stopCycle() {
    log.debug "Stopping Aurora Borealis cycle"
    unschedule(cycleToNextColor)
}

def cycleToNextColor() {
    // Aurora Borealis color palette - harmonious blues, greens, purples, and pinks
    def colors = [
        [hue: 40, saturation: 85],   // Green-blue
        [hue: 50, saturation: 90],   // Cyan-green
        [hue: 35, saturation: 80],   // Aqua
        [hue: 65, saturation: 75],   // Blue-green
        [hue: 25, saturation: 70],   // Teal
        [hue: 75, saturation: 80],   // Sky blue
        [hue: 85, saturation: 85],   // Purple-blue
        [hue: 90, saturation: 70],   // Violet
        [hue: 95, saturation: 65],   // Purple-pink
        [hue: 30, saturation: 75]    // Green-teal
    ]
    
    def colorIndex = state.currentColorIndex ?: 0
    def nextColor = colors[colorIndex]
    
    log.debug "Transitioning to color ${colorIndex}: Hue=${nextColor.hue}, Sat=${nextColor.saturation}"
    
    lights.each { light ->
        light.setLevel(brightness, transitionTime)
        light.setColor([
            hue: nextColor.hue,
            saturation: nextColor.saturation,
            level: brightness
        ])
    }
    
    // Move to next color in the palette
    state.currentColorIndex = (colorIndex + 1) % colors.size()
    
    // Schedule next color change
    def totalTime = transitionTime + colorHoldTime
    runIn(totalTime, cycleToNextColor)
}

def uninstalled() {
    unschedule()
}
