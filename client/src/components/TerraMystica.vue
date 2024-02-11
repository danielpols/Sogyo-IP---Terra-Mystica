<script setup>
    import HexArray from './HexArray.vue'
    import PlayerArray from './PlayerArray.vue';
</script>

<template>
    <div class="gameScreen" @wheel="zoomBoard">
        <HexArray :zoomLevel="boardZoomLevel"/>
    </div>
    <div class="playerScreen">
        <PlayerArray/>
    </div>
</template>

<script>
export default {
    data () {
        return {
            boardZoomLevel: 1
        }
    },
    methods: {
        zoomBoard(event) {
            this.boardZoomLevel *= 1-event.deltaY/1000;
            if(this.boardZoomLevel < 0.5) {
                this.boardZoomLevel = 0.5;
            }
            if(this.boardZoomLevel > 3) {
                this.boardZoomLevel = 3;
            }
        }
    }
}
</script>

<style scoped>
.gameScreen {
    display: inline-block;
    width: 60%;
    padding: 3%;
    border: 5px solid brown;
    overflow: hidden    ;
}

.playerScreen {
    display: inline-block;
    width: 70%;
}
</style>

