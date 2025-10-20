# Aurora Borealis Lighting

A Hubitat Elevation app that creates mesmerizing aurora borealis (northern lights) effects by cycling your color-capable smart lights through a carefully curated palette of harmonious colors with smooth transitions.

## Features

- **Harmonious Color Palette**: Cycles through 10 carefully selected colors inspired by the natural aurora borealis, including blues, greens, teals, purples, and subtle pinks
- **Smooth Transitions**: Configurable transition times create seamless color changes that mimic the gentle flowing of northern lights
- **Adjustable Timing**: Customize both transition duration and color hold time to match your preferences
- **Brightness Control**: Set a consistent brightness level across all lights
- **Optional Switch Control**: Enable or disable the effect using any smart switch
- **Multi-Light Support**: Control multiple color-capable lights simultaneously for a coordinated display
- **Automatic Cycling**: Continuously loops through the color palette without manual intervention

## Requirements

- Hubitat Elevation hub (C-3, C-5, C-7, or C-8)
- One or more color-capable smart lights (bulbs, strips, etc.) that support the Color Control capability
- Hubitat app code installation capability

## Installation

### Step 1: Install the App Code

1. Log into your Hubitat web interface
2. Navigate to **Apps Code** from the left sidebar
3. Click **+ New App** in the top right
4. Copy the entire contents of `AuroraBorealisLighting.groovy` and paste it into the code editor
5. Click **Save**

### Step 2: Create an App Instance

1. Navigate to **Apps** from the left sidebar
2. Click **+ Add User App** in the top right
3. Select **Aurora Borealis Lighting** from the list
4. Configure your settings (see Configuration section below)
5. Click **Done**

## Configuration

### Select Lights
- **Which color lights?**: Select one or more color-capable lights that will participate in the aurora effect

### Timing Settings
- **Transition time between colors**: Duration (in seconds) for the color change animation. Default is 30 seconds. Longer times create more gradual, flowing transitions.
- **Time to hold each color**: Duration (in seconds) to maintain each color before transitioning to the next. Default is 10 seconds.

### Brightness
- **Brightness level**: Sets the brightness for all selected lights (1-100). Default is 75.

### Control (Optional)
- **Enable/disable with this switch**: Optionally link a smart switch to control when the aurora effect is active. When the switch turns on, the effect starts. When off, it stops.

## Color Harmony Logic

The aurora borealis effect uses a scientifically-inspired color palette that mimics the natural phenomenon:

### Color Palette
The app cycles through 10 colors in the following sequence:

1. **Green-blue** (Hue: 40, Sat: 85) - The dominant aurora color
2. **Cyan-green** (Hue: 50, Sat: 90) - Bright, energetic green
3. **Aqua** (Hue: 35, Sat: 80) - Cool, refreshing tone
4. **Blue-green** (Hue: 65, Sat: 75) - Deeper oceanic shade
5. **Teal** (Hue: 25, Sat: 70) - Rich, balanced tone
6. **Sky blue** (Hue: 75, Sat: 80) - Clear, bright blue
7. **Purple-blue** (Hue: 85, Sat: 85) - Transitional to violet
8. **Violet** (Hue: 90, Sat: 70) - Deep purple
9. **Purple-pink** (Hue: 95, Sat: 65) - Rare aurora pink
10. **Green-teal** (Hue: 30, Sat: 75) - Returns toward green to loop smoothly

### Why These Colors?

- **Natural Aurora Range**: Real aurora borealis displays primarily show green (most common), with occasional blue, purple, and rare pink/red
- **Smooth Transitions**: Colors are ordered to create gradual hue shifts, avoiding jarring jumps
- **Saturation Balance**: High saturation (65-90%) ensures vibrant colors while maintaining visual comfort
- **Cyclical Design**: The palette loops seamlessly from the last color back to the first

### How Transitions Work

1. The app sets the target color and brightness
2. Your smart lights gradually transition over the configured transition time
3. The color holds steady for the configured hold time
4. The cycle advances to the next color
5. The process repeats indefinitely in a loop

**Total cycle time**: (Transition time + Hold time) × 10 colors
- With defaults (30s + 10s): 400 seconds (~6.7 minutes) per complete cycle

## Usage Notes

### Starting the Effect

- **Without a control switch**: The effect starts automatically when you click "Done" in the app configuration
- **With a control switch**: Turn the linked switch ON to start the effect

### Stopping the Effect

- **Without a control switch**: Open the app instance and modify settings or uninstall the app
- **With a control switch**: Turn the linked switch OFF to stop the effect

### Tips for Best Results

1. **Use similar lights**: For the most cohesive effect, use lights of the same brand/model
2. **Adjust transition time**: Longer transitions (45-60 seconds) create more dreamlike effects; shorter times (15-20 seconds) are more dynamic
3. **Consider room size**: Larger rooms may benefit from higher brightness levels
4. **Evening use**: The effect works best in darker environments where colors are more visible
5. **Combine with other lights**: Keep some lights on normal white/warm settings to maintain functionality while enjoying the aurora effect on accent lights

### Troubleshooting

- **Colors look wrong**: Some lights may interpret HSV colors differently. Try adjusting brightness or selecting different lights.
- **Transitions are choppy**: Check that your lights are within good range of your hub and that your mesh network is strong.
- **Effect doesn't start**: Ensure selected lights support the Color Control capability and are powered on.
- **Effect stops unexpectedly**: Check Hubitat logs (Settings → Logs) for any error messages.

## Customization

Advanced users can modify the color palette by editing the `colors` array in the `cycleToNextColor()` function:

```groovy
def colors = [
    [hue: 40, saturation: 85],   // Modify these values
    // Add or remove color entries as desired
]
```

- **Hue**: 0-100 (maps to 0-360° on the color wheel)
- **Saturation**: 0-100 (0 = white, 100 = full color)

## License

Licensed under the Apache License, Version 2.0. See the LICENSE file or visit http://www.apache.org/licenses/LICENSE-2.0

## Contributing

Feel free to submit issues, feature requests, or pull requests to improve this app!

## Credits

Inspired by the natural phenomenon of aurora borealis and designed to bring a touch of that magic into your smart home.
