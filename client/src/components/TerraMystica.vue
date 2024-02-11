<script setup>
    import HexArray from './HexArray.vue'
    import PlayerArray from './PlayerArray.vue';
</script>

<template>
    <div class="gameScreen" @wheel.prevent="zoomBoard" @mousedown.left.capture="pan=true" @mouseup.left.capture="pan=false" v-on="pan ? {'mousemove': panBoard} : {}" ref="board">
        <HexArray :zoomLevel="boardZoomLevel" :offsetX="boardOffset.x+'px'" :offsetY="boardOffset.y+'px'"/>
    </div>
    <div class="playerScreen">
        <PlayerArray/>
    </div>
</template>

<script>
export default {
    data () {
        return {
            boardZoomLevel: 1,
            boardOffset: {x: 0, y:0},
            pan: false
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
        },
        panBoard(event) {
            const scale = 1
            this.boardOffset.x += scale * event.movementX;
            this.boardOffset.y += scale * event.movementY;
            if(this.boardOffset.x < -this.$refs.board.clientWidth/2) {
                this.boardOffset.x = -this.$refs.board.clientWidth/2
            }
            if(this.boardOffset.x > this.$refs.board.clientWidth/2) {
                this.boardOffset.x = this.$refs.board.clientWidth/2
            }
            if(this.boardOffset.y < -this.$refs.board.clientHeight/2) {
                this.boardOffset.y = -this.$refs.board.clientHeight/2
            }
            if(this.boardOffset.y > this.$refs.board.clientHeight/2) {
                this.boardOffset.y = this.$refs.board.clientHeight/2
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

