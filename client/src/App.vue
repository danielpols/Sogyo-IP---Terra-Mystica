<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
import TerraMystica from './components/TerraMystica.vue'
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
    <template v-else><TheWelcome /></template>
    <button type="button" class="switchButton" v-on:click="swap = !swap">Hi {{ swap }}</button>
  </main>
</template>

<script>
  export default {
    data: function() {
      return {
        swap: false
      }
    }
  }
</script>

<style scoped>
</style>
