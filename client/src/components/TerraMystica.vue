<script setup>
    import HexArray from './HexArray.vue'
    import PlayerArray from './PlayerArray.vue';
</script>

<template>
    <div class="gameScreen" @wheel.prevent="zoomBoard" @mousedown.middle.capture="pan=true" @mouseup.middle.capture="pan=false" @mouseleave="pan=false"
            v-on="pan ? {'mousemove': panBoard} : {}" ref="boardContainer">
        <HexArray :zoomLevel="boardZoomLevel" :offsetX="boardOffset.x+'px'" :offsetY="boardOffset.y+'px'" ref="board"/>
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
            boardOffset: {x: 0, y: 0},
            boardPanMargins: {x: 0, y: 0},
            pan: false
        }
    },
    methods: {
        zoomBoard(event) {
            const unpannedDX = (z) => event.pageX-this.$refs.board.$el.offsetLeft-this.$refs.board.$el.clientWidth*(1-z)/2
            const unpannedDY = (z) => event.pageY-this.$refs.board.$el.offsetTop-this.$refs.board.$el.clientHeight*(1-z)/2

            const mouseX = (z) => (unpannedDX(z) - this.boardOffset.x)/z;    
            const mouseY = (z) => (unpannedDY(z) - this.boardOffset.y)/z;
            
            const oldZoom = this.boardZoomLevel;

            this.boardZoomLevel *= 1-event.deltaY/1000;
            if(this.boardZoomLevel < 0.5) {
                this.boardZoomLevel = 0.5;
            }
            if(this.boardZoomLevel > 3) {
                this.boardZoomLevel = 3;
            }

            console.log(mouseX(this.boardZoomLevel), mouseY(this.boardZoomLevel));
            this.boardOffset.x = unpannedDX(this.boardZoomLevel)-this.boardZoomLevel*mouseX(oldZoom);
            this.boardOffset.y = unpannedDY(this.boardZoomLevel)-this.boardZoomLevel*mouseY(oldZoom);
        },
        panBoard(event) {
            this.boardOffset.x += event.movementX;
            this.boardOffset.y += event.movementY;

            const paddingX = Math.abs((this.$refs.boardContainer.clientWidth - this.$refs.board.$el.clientWidth * this.boardZoomLevel)/2);

            const offsetY = this.$refs.board.$el.clientWidth * 0.0222; // offset from margin
            const paddingY = Math.abs((this.$refs.boardContainer.clientHeight - (this.$refs.board.$el.clientHeight + offsetY) * this.boardZoomLevel)/2);

            console.log(this.boardPanMargins.x, this.boardPanMargins.y)

            if(Math.abs(this.boardOffset.x) > paddingX) {
                this.boardOffset.x = Math.sign(this.boardOffset.x) * paddingX;
            }

            if(Math.abs(this.boardOffset.y) > paddingY) {
                this.boardOffset.y = Math.sign(this.boardOffset.y) * paddingY;
            }
        }
    },
    mounted() {
        this.boardPanMargins.x = this.$refs.boardContainer.clientWidth / 4;
        this.boardPanMargins.y = this.$refs.boardContainer.clientHeight / 4;
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

