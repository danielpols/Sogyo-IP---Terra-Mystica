<script setup>
    import '../css/hexcss.css'
    import { shallowRef } from 'vue';
    import { gameState, tileColors } from '@/global';
    import None from './buildings/None.vue';
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
                    <button type="button" class="tileButton" @click="icon=shallowRef(Dwelling)" :style="buttonCSS" :disabled="tile.terrain=='RIVER'"
                    ><component :is="icon"/></button>
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

