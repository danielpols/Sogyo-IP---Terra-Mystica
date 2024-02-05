<script setup>
import TerraMystica from './components/TerraMystica.vue'
import StartScreen from './components/StartScreen.vue';
import { gameState } from './global.js';
</script>

<template>
  <main>
    <template v-if="gameState.state.board"><TerraMystica/></template>
    <template v-else><StartScreen/></template>
  </main>
</template>

<script>
</script>

<style scoped>
main {
  text-align: center;
}
</style>
