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
        await fetch('terra/api/start', {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            content: "hi"
          })
        }).then(response => response.json())
        .then(data => {console.log("Success!"); gameState.state = data})
        .catch(error => console.log(error));
      }
    }
  }
</script>

<style scoped>
main {
  text-align: center;
}
</style>
