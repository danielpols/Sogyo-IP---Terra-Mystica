<script setup>
    import '../css/hexcss.css'
</script>

<template>
    <li class="tileListItem">
        <div class="tileContainer" :style="borderCSS">
            <div class="tileBorder">
                <a class="tile">
                    <button type="button" class="tileButton" @click="post" :style="buttonCSS" :disabled="terrain=='RIVER'"
                    >{{ terrain }}</button>
                </a>
            </div>
        </div>
    </li>
</template>

<script>
  export default {
    props: ['terrain'],
    methods: {
        async post() {
            await fetch('terra/api/log').then(response => response.text())
            .then(data => console.log(data))
            .catch(error => console.log(error));
        }
    },
    computed: {
        buttonCSS() {
            var colour = "transparent";
            if(this.terrain == "PLAINS") {
                colour = "saddlebrown";
            } else if(this.terrain == "SWAMP") {
                colour = "dimgrey";
            } else if(this.terrain == "LAKE") {
                colour = "royalblue";
            } else if(this.terrain == "FOREST") {
                colour = "green";
            } else if(this.terrain == "MOUNTAINS") {
                colour = "silver";
            } else if(this.terrain == "WASTELAND") {
                colour = "darkred";
            } else if(this.terrain == "DESERT") {
                colour = "orange";
            } else {
                colour = "navy";
            }
            var textColour = (this.terrain == "RIVER") ? "dimgrey" : "black";
            return {
                '--bg-color': colour,
                '--text-color': textColour
            };
        },
        borderCSS() {
            var borderColour = (this.terrain == "RIVER") ? "transparent" : "white";
            return {
                '--border-color': borderColour
            }
        }
    }
  }
</script>

<style scoped src="../css/hexcss.css">
</style>

