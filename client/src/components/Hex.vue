<script setup>
    import { tileColors, doAction, getIcon } from '@/global';
import Building from './Building.vue';
</script>

<template>
    <button type="button" class="tileButton" @click="$emit('togglePopup')" :style="buttonCSS" :disabled="tile.actions.length==0"
    :key="tile.building"><Building :icon="getIcon(tile.building)" :scale="0.4"/>
        {{ tile.building=="NONE" && tile.actions.length > 0 && tile.actions[0].terraformCost > 0 
            ? tile.actions[0].terraformCost+" V" : "" }}
    </button>
</template>

<script>
  export default {
    props: ['tile'],
    emits: ['togglePopup'],
    methods: {
        async act() {
            await doAction(this.tile.actions[0]);
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

