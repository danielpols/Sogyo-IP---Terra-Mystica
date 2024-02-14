<script setup>
    import { gameState, tileColors } from '@/global';
import Building from './Building.vue';
</script>

<template>
    <button type="button" class="tileButton" @click="$emit('togglePopup')" :style="buttonCSS" :disabled="tile.actions.length==0"
    :key="tile.building"><Building :icon="getIcon(tile.building)"/>
    </button>
</template>

<script>
  export default {
    props: ['tile'],
    emits: ['togglePopup'],
    methods: {
        async build() {
            await fetch('terra/api/build', {
                method: "POST",
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    location: this.tile.location,
                    building: "DWELLING"
                })
            }).then(response => response.json())
            .then(data => gameState.state = data)
            .catch(error => console.log(error));
        },
        getIcon(building) {
            return building.substring(0, 1) + building.substring(1).toLowerCase();
        }
    },
    computed: {
        buttonCSS() {
            var textColour = (this.tile.terrain == "RIVER") ? "dimgrey" : "black";
            return {
                '--bg-color': tileColors(this.tile.terrain),
                '--text-color': textColour
            };
        }
    }
  }
</script>

<style scoped>
.tileButton {
    border-width: 0;
    padding: 0;
    width: 100%;
    height: 100%;
    background-color: var(--bg-color);
    color: var(--text-color);
    font-size: 1vw;

    overflow: visible;
}

.tileButton:disabled {
    pointer-events: none;
}

.tileButton:hover:enabled {
    cursor: pointer;
    filter: brightness(0.7);
}
</style>

