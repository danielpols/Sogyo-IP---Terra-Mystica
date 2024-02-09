<script setup>
    import '../css/hexcss.css'
    import { tileColors } from '@/global';
    import Dwelling from './buildings/Dwelling.vue';
    import TradingCity from './buildings/TradingCity.vue';
    import Stronghold from './buildings/Stronghold.vue';
    import Temple from './buildings/Temple.vue';
    import Sanctuary from './buildings/Sanctuary.vue';
</script>

<template>
    <li class="tileListItem">
        <div class="tileContainer" :style="borderCSS">
            <div class="tileBorder">
                <a class="tile">
                    <button type="button" class="tileButton" @click="post" :style="buttonCSS" :disabled="tile.terrain=='RIVER'"
                    ><component :is="building"/></button>
                </a>
            </div>
        </div>
    </li>
</template>

<script>
  export default {
    props: ['tile'],
    data () {
        return {
            building: "Temple"
        }
    },
    methods: {
        async post() {
            await fetch('terra/api/log').then(response => response.text())
            .then(data => console.log(data))
            .catch(error => console.log(error));
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
        borderCSS() {
            var borderColour = (this.tile.terrain == "RIVER") ? "transparent" : "white";
            return {
                '--border-color': borderColour
            }
        }
    },
    components: {
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

