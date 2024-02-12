<script setup>
    import { gameState, tileColors } from '@/global';
import Building from './Building.vue';
import HexPopup from './HexPopup.vue';
</script>

<template>
    <button type="button" class="tileButton" @click="popup = !popup" :style="buttonCSS" :disabled="!tile.buildable"
    :key="tile.building"><Building :icon="getIcon(tile.building)"/>
        <HexPopup class="tilePopup" :style="popupCSS"/>
    </button>
</template>

<script>
  export default {
    props: ['tile'],
    data () {
        return {
            popup: false
        }
    },
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
        },
        popupCSS() {
            return {
                '--popup-visibility': this.popup ? "visible" : "hidden"
            }
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

.tilePopup {
    visibility: var(--popup-visibility);
}
</style>

