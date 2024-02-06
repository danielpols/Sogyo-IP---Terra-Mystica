<script setup>
    import HexArray from './HexArray.vue'
    import PlayerArray from './PlayerArray.vue';
</script>

<template>
    <div class="gameScreen">
        <HexArray/>
    </div>
    <div class="playerScreen">
        <PlayerArray/>
    </div>
</template>

<script>
</script>

<style scoped>
.gameScreen {
    display: inline-block;
    height: 80%;
    width: 80%;
}

.playerScreen {
    display: inline-block;
    height: 40%;
    width: 80%;
}
</style>

