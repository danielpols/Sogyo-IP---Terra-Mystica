<script setup>
import TerraMystica from './components/TerraMystica.vue'
import StartScreen from './components/StartScreen.vue';
import { gameState } from './global.js';
</script>

<template>
  <main>
    <template v-if="gameState.state.board"><TerraMystica/></template>
    <template v-else><StartScreen/></template>
    <br/>
    <button type="button" v-on:click="print">Log</button>
  </main>
</template>

<script>
  export default {
    data () {
      return {
        swap: false
      }
    },
    methods: {
      print() {console.log(gameState);}
    }
  }
</script>

<style scoped>
main {
  text-align: center;
}
</style>
