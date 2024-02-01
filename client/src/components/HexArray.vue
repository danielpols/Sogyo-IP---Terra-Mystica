<script setup>
    import { gameState } from '@/global';
    import Hex from './Hex.vue';
</script>

<template>
    <ul class="tileList">
        <Hex v-if="gameState.state.board" v-for="tile in gameState.state.board.tiles"/>
    </ul>
</template>

<script>

</script>

<style scoped>
.tileList {
    position: relative;
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 10px;
    padding: 0;
    width: 346.41px;
    min-height: 450px;
    margin-bottom: 60px;
    font-size: 0;
}

.tileList * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}
</style>

