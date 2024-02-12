<script setup>
    import Hex from './Hex.vue';
</script>

<template>
    <div class="tileContainer" :style="borderCSS">
        <div class="tileBorder">
            <a class="tile">
                <Hex :tile="tile"/>
            </a>
        </div>
    </div>
</template>

<script>
  export default {
    props: ['tile'],
    computed: {
        borderCSS() {
            var borderColour = this.tile.buildable ? "white" : "grey";
            if(this.tile.terrain == "RIVER") {
                borderColour = "transparent";
            }
            return {
                '--border-color': borderColour
            }
        }
    }
  }
</script>

<style scoped>
.tileContainer, .tileContainer * {
    position: absolute;
    pointer-events: all;
}

.tileContainer {
    overflow: hidden;
    visibility: hidden;
    background-color: transparent;
    width: 100%;
    padding-bottom: 115.47%;
    transform: rotate3d(0, 0, 1, -60deg) skewY(30deg);
}

.tileContainer::before {
    content: "";
    display: block;
    position: absolute;
    overflow: hidden;
    visibility: visible;
    background-color: var(--border-color);
    clip-path: polygon(50% 0, 100% 0, 100% 50%, 50% 100%, 0 100%, 0 50%);
    width: 100%;
    padding-bottom: 115.47%;
    z-index: -1;
}

.tileContainer:has(.tileButton:hover:enabled)::before {
    background-color: transparent;
}

.tileBorder {
    overflow: hidden;
    margin: 5%;
    width: 90%;
    height: 90%;
}

.tile {
    display: block;
    overflow: hidden;
    visibility: visible;
    width: 100%;
    height: 100%;
    transform: skewY(-30deg) rotate3d(0, 0, 1, 60deg);
}
</style>

