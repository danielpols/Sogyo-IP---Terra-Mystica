<script setup>
    import '../css/hexcss.css'
    import { gameState, tileColors } from '@/global';
    import None from './buildings/None.vue';
    import Dwelling from './buildings/Dwelling.vue';
    import TradingCity from './buildings/TradingCity.vue';
    import Stronghold from './buildings/Stronghold.vue';
    import Temple from './buildings/Temple.vue';
    import Sanctuary from './buildings/Sanctuary.vue';
</script>

<template>
    <button type="button" class="tileButton" @click="build" :style="buttonCSS" :disabled="!tile.buildable"
    :key="tile.building"><component :is="icon"/></button>
</template>

<script>
  export default {
    props: ['tile'],
    data () {
        return {
            icon: "None"
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
        updateIcon() {
            if(this.tile.building == "DWELLING") {
                this.icon = "Dwelling";
            }
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
    },
    beforeUpdate () {
        this.updateIcon();
    },
    components: {
        None,
        Dwelling,
        TradingCity,
        Stronghold,
        Temple,
        Sanctuary
    }
  }
</script>

<style scoped src="../css/hexcss.css">
</style>

