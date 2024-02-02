<script setup>

</script>

<template>
    <li class="tileListItem">
        <div class="tileContainer">
            <a class="tile">
                <button type="button" class="tileButton" @click="post" :style="cssVars" :disabled="terrain=='RIVER'"
                >{{ terrain }}</button>
            </a>
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
        cssVars() {
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
        }
    }
  }
</script>

<style scoped>
.tileListItem {
    position: relative;
    display: inline;
    box-sizing: border-box;
    grid-column-end: span 2;
    outline: 1px solid transparent;
    border-bottom: 1px solid transparent;
    width: 100%;
    padding-bottom: 86.602%;
}

.tileListItem * {
    margin: 0;
    padding: 0;
}

.tileListItem:nth-child(25n+1) {
    grid-column-start: 1;
}

.tileListItem:nth-child(25n+14) {
    grid-column-start: 2;
}

.tileContainer, .tileContainer * {
    position: absolute;
}

.tileContainer {
    overflow: hidden;
    visibility: hidden;
    width: 100%;
    padding-bottom: 115.47%;
    transform: rotate3d(0, 0, 1, -60deg) skewY(30deg);
}

.tile {
    display: block;
    overflow: hidden;
    visibility: visible;
    width: 100%;
    height: 100%;
    transform: skewY(-30deg) rotate3d(0, 0, 1, 60deg);
}

.tileButton {
    border-width: 0;
    width: 100%;
    height: 100%;
    background-color: var(--bg-color);
    color: var(--text-color);
    font-size: 8px;
}
</style>

