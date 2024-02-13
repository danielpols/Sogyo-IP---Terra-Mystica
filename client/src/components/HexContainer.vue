<script setup>
    import Hex from './Hex.vue';
</script>

<template>
    <div class="tileBorder" :style="borderCSS">
        <div class="tileContainer">
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

.tileBorder {
    position: absolute;
    pointer-events: all;
    overflow: hidden;
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
    overflow: hidden;
    visibility: visible;
    background-color: var(--border-color);
    clip-path: polygon(50% 0, 100% 0, 100% 50%, 50% 100%, 0 100%, 0 50%);
    width: 100%;
    padding-bottom: 115.47%;
    z-index: -1;
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
</style>

