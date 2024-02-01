<script setup>
    import { gameState } from '@/global';
    import Hex from './Hex.vue';
</script>

<template>
    <ul class="tileList">
        <Hex v-if="gameState.state.board" v-for="tile in gameState.state.board.tiles" :terrain="tile.terrain"/>
    </ul>
</template>

<script>

</script>

<style scoped>
.tileList {
    box-sizing: border-box;
    position: relative;
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(26, 1fr);
    grid-gap: 3px;
    margin: 0;
    padding: 0;
    width: 100%;
    margin-bottom: 60px;
    font-size: 0;
}
</style>

