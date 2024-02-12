<script setup>
    import { gameState } from '@/global';
    import Hex from './Hex.vue';
import HexContainer from './HexContainer.vue';
</script>

<template>
    <ul class="tileList">
        <li class="tileListItem" v-if="gameState.state.board" v-for="tile in gameState.state.board.tiles" >
            <HexContainer :tile="tile"/>
        </li>
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

.tileListItem {
    position: relative;
    display: inline;
    box-sizing: border-box;
    grid-column-end: span 2;
    outline: 1px solid transparent;
    border-bottom: 1px solid transparent;
    width: 100%;
    padding-bottom: 86.602%;
    pointer-events: none;
}

.tileListItem:nth-child(25n+1) {
    grid-column-start: 1;
}

.tileListItem:nth-child(25n+14) {
    grid-column-start: 2;
}
</style>

