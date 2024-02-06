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
                    '--player-count': gameState.state.players.length
                }
            }
        }
    }

</script>

<style scoped>
.playerList {
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(var(--player-count), 1fr);
    grid-gap: 5%;
}
</style>