<script setup>
    import { gameState } from '@/global';
    import Hex from './Hex.vue';
</script>

<template>
    <ul class="tileList">
        <Hex v-if="gameState.state.board" v-for="tile in gameState.state.board.tiles" :tile="tile"/>
    </ul>
</template>

<script>

export default {
    props: ['zoomLevel', 'offsetX', 'offsetY']
}

</script>

<style scoped>
.tileList {
    box-sizing: border-box;
    position: relative;
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(26, 1fr);
    overflow: visible;
    margin: 0;
    padding: 0;
    width: 100%;
    margin-bottom: 2.22%;
    text-align: left;
    font-size: 0;
    transform: translate(v-bind(offsetX), v-bind(offsetY)) scale(v-bind(zoomLevel)) ;
}
</style>

