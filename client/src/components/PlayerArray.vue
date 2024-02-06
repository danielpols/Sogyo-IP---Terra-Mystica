<script setup>
    import Player from './Player.vue';
    import { gameState } from '@/global';
</script>

<template>
    <ul class="playerList" :style="listCSS">
        <Player v-for="player in gameState.state.players" :player="player"/>
    </ul>
</template>

<script>
    export default {
        computed: {
            listCSS() {
                return {
                    '--column-amount': gameState.state.players.length + 1
                }
            }
        }
    }

</script>

<style scoped>
.playerList {
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(calc(var(--column-amount)), 1fr);
    grid-template-rows: repeat(3, 1fr);
    grid-gap: 5%;
}
</style>