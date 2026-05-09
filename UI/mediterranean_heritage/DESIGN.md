---
name: Mediterranean Heritage
colors:
  surface: '#041329'
  surface-dim: '#041329'
  surface-bright: '#2c3951'
  surface-container-lowest: '#010e24'
  surface-container-low: '#0d1c32'
  surface-container: '#112036'
  surface-container-high: '#1c2a41'
  surface-container-highest: '#27354c'
  on-surface: '#d6e3ff'
  on-surface-variant: '#c4c6cf'
  inverse-surface: '#d6e3ff'
  inverse-on-surface: '#233148'
  outline: '#8e9199'
  outline-variant: '#43474e'
  surface-tint: '#adc8f5'
  primary: '#adc8f5'
  on-primary: '#133155'
  primary-container: '#1e3a5f'
  on-primary-container: '#8aa4cf'
  inverse-primary: '#455f87'
  secondary: '#e9c349'
  on-secondary: '#3c2f00'
  secondary-container: '#af8d11'
  on-secondary-container: '#342800'
  tertiary: '#a7c8ff'
  on-tertiary: '#03305f'
  tertiary-container: '#133a69'
  on-tertiary-container: '#84a5da'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#d5e3ff'
  primary-fixed-dim: '#adc8f5'
  on-primary-fixed: '#001c3b'
  on-primary-fixed-variant: '#2d486d'
  secondary-fixed: '#ffe088'
  secondary-fixed-dim: '#e9c349'
  on-secondary-fixed: '#241a00'
  on-secondary-fixed-variant: '#574500'
  tertiary-fixed: '#d5e3ff'
  tertiary-fixed-dim: '#a7c8ff'
  on-tertiary-fixed: '#001b3b'
  on-tertiary-fixed-variant: '#234777'
  background: '#041329'
  on-background: '#d6e3ff'
  surface-variant: '#27354c'
typography:
  display-lg:
    fontFamily: Noto Serif
    fontSize: 40px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Noto Serif
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Noto Serif
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
  body-md:
    fontFamily: Noto Serif
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.5'
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.2'
    letterSpacing: 0.05em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 12px
  md: 24px
  lg: 48px
  xl: 64px
  container-margin: 20px
  gutter: 16px
---

## Brand & Style

The design system is anchored in a **Modern-Historical** style, blending the timeless elegance of Tunisian heritage with a sleek, premium digital interface. It targets a culturally curious audience, evoking a sense of prestige, intellectual discovery, and serene exploration.

The visual narrative relies on a "Curated Museum" aesthetic: high-contrast typography, ample negative space, and deep, atmospheric colors. Subtle heritage-inspired textures—such as faint geometric zellige patterns or weathered stone overlays—should be used sparingly as background watermarks to provide depth without introducing clutter.

## Colors

The palette is dominated by the **Deep Mediterranean Blue**, which serves as the foundation for the user's journey. 

- **Primary:** The Deep Mediterranean Blue is used for high-importance interactions and primary buttons.
- **Surface:** The main background uses a Very Dark Blue/Charcoal to minimize eye strain and make the accent colors pop.
- **Accents:** Subtle Gold is reserved exclusively for "Reward Moments"—achievements, gold medals, and streaks—to maintain its perceived value.
- **Text:** High-contrast white is used for body copy and titles, while soft grey is used for captions and secondary metadata to establish a clear hierarchy.

## Typography

This design system utilizes **Noto Serif** as the primary typeface for both headings and body text. This choice reinforces the historical and literary theme of the app. 

To prevent the interface from feeling too dated, **Inter** is introduced as a secondary functional font for labels, buttons, and micro-copy. This provides a crisp, legible contrast to the elegant serifs. Headlines should utilize tighter letter-spacing to feel more "editorial," while small labels should be slightly tracked out and set in uppercase for maximum clarity.

## Layout & Spacing

The layout follows a **Fixed-Grid** philosophy on mobile and a centered **Fluid-Grid** on larger displays. A strict 8px rhythmic scale is used to define all margins and padding.

Generous white space (or "dark space") is essential to the premium feel. Content containers should have significant internal padding (24px) to allow the historical imagery and serif typography to "breathe." Avoid density; if an interface feels crowded, increase the vertical rhythm to the `lg` or `xl` spacing units.

## Elevation & Depth

Hierarchy is established through **Tonal Layering** rather than traditional heavy shadows. 

1. **Base Layer:** The deepest charcoal (#0A192F).
2. **Surface Layer:** Darker blue-grey (#162A45) used for cards and modular containers.
3. **Interactive Layer:** The Primary blue, used sparingly for focused elements.

To add a touch of sophistication, use **Low-Contrast Outlines**. Apply a 1px border with 10% white opacity to cards to give them a subtle "glass" edge without the blur, helping them stand out against the deep background.

## Shapes

The design system employs a **Rounded** shape language to balance the "serious" nature of the serif typography. A consistent 12px to 16px corner radius is applied to all primary containers and buttons.

This "friendly" roundness prevents the app from feeling like a dry textbook, making the heritage discovery process feel accessible and modern.

## Components

- **Buttons:** Primary buttons use the Mediterranean Blue with white text. Secondary buttons should use the low-contrast outline style with a subtle hover state that increases the background brightness by 5%.
- **Quiz Cards:** Large, rounded containers with the 1px subtle border. Use the Gold accent for a progress bar at the top of the card to indicate completion.
- **Achievements/Chips:** Small, pill-shaped elements. When an achievement is unlocked, the chip should transition from the surface color to a Gold-tinted gradient.
- **Inputs:** Dark, recessed fields with a 1px border that turns Gold when focused.
- **Heritage Tiles:** Used for monument selection. These should feature high-quality photography with a dark gradient overlay at the bottom to ensure the white serif titles remain legible.
- **Progress Indicators:** Use thin, elegant lines. Avoid chunky bars to maintain the minimal, professional aesthetic.