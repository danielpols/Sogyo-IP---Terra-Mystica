<script setup>
    import Hex from './Hex.vue';
    import HexPopup from './HexPopup.vue';
</script>

<template>
    <HexPopup class="tilePopup" :style="popupCSS"/>
    <div class="tileBorder" :style="borderCSS">
        <div class="tileContainer">
            <a class="tile">
                <Hex :tile="tile" @toggle-popup="showPopup = !showPopup; showPopup ? $emit('togglePopup', index) : ''"/>
            </a>
        </div>
    </div>
</template>

<script>
  export default {
    props: ['tile', 'index'],
    emits: ['togglePopup'],
    expose: ['disablePopup'],
    data () {
        return {
            showPopup: false
        }
    },
    methods: {
        disablePopup() {
            this.showPopup = false;
        }
    },
    computed: {
        borderCSS() {
            var borderColour = this.tile.buildable ? "white" : "grey";
            if(this.tile.terrain == "RIVER") {
                borderColour = "transparent";
            }
            return {
                '--border-color': borderColour
            }
        },
        popupCSS() {
            return {
                '--popup-visibility': this.showPopup ? 'visible' : 'hidden',
                '--popup-hover-opacity' : this.showPopup ? 1 : 0.5
            }
        }
    }
  }
</script>

<style scoped>

.tileBorder {
    position: absolute;
    pointer-events: all;
    visibility: hidden;
    background-color: transparent;
    width: 100%;
    padding-bottom: 115.47%;
    transform: rotate3d(0, 0, 1, -60deg) skewY(30deg);
}

.tileBorder::before {
    content: "";
    display: block;
    position: absolute;
    visibility: visible;
    background-color: var(--border-color);
    clip-path: polygon(50% 0, 100% 0, 100% 50%, 50% 100%, 0 100%, 0 50%);
    width: 100%;
    padding-bottom: 115.47%;
}

.tileBorder:has(.tileButton:hover:enabled)::before {
    background-color: transparent;
}

.tileContainer {
    position: absolute;
    overflow: hidden;
    margin: 5%;
    width: 90%;
    height: 90%;
}

.tile {
    display: block;
    visibility: visible;
    width: 100%;
    height: 100%;
    transform: skewY(-30deg) rotate3d(0, 0, 1, 60deg);
}

.tilePopup {
    display: block;
    position: absolute;
    z-index: 1;
    visibility: var(--popup-visibility);

    width: 100%;

    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

.tilePopup:has(~ .tileBorder .tileButton:enabled:hover) {
    visibility: visible;
    opacity: var(--popup-hover-opacity);
}
</style>

