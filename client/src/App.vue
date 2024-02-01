<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TerraMystica from './components/TerraMystica.vue'
import { gameState } from './global.js';
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>
  </header>

  <main>
    <template v-if="swap"><TerraMystica/></template>
    <button type="button" class="switchButton" v-on:click="swap = !swap">Hi {{ swap }}</button>
    <button type="button" v-on:click="print">Log</button>
    <button type="button" v-on:click="getGameState">Fetch!</button>
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
      print() {console.log(gameState);},
      async getGameState() {
        await fetch('terra/api/get-gamestate').then(response => response.json())
        .then(data => {console.log("Success!"); console.log(data); gameState.state = data})
        .catch(error => console.log(error));
      }
    }
  }
</script>

<style scoped>
</style>
