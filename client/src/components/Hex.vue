<script setup>
    import '../css/hexcss.css'
    import { tileColors } from '@/global';
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
            var textColour = (this.terrain == "RIVER") ? "dimgrey" : "black";
            return {
                '--bg-color': tileColors(this.terrain),
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

