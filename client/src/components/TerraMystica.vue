<script setup>
    import { gameState } from '@/global';
    import HexArray from './HexArray.vue'
    import PlayerArray from './PlayerArray.vue';
</script>

<template>
    <div class="banner"> {{ gameState.state.message }} </div>
    <div class="gameScreen" @wheel.prevent="zoomBoard" @mousedown.middle.prevent.capture="pan=true" @mouseup.middle.capture="pan=false" @mouseleave="pan=false"
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
            if(this.boardZoomLevel < 0.6) {
                this.boardZoomLevel = 0.6;
            }
            if(this.boardZoomLevel > 3) {
                this.boardZoomLevel = 3;
            }

            this.boardOffset.x = unpannedDX(this.boardZoomLevel)-this.boardZoomLevel*mouseX(oldZoom);
            this.boardOffset.y = unpannedDY(this.boardZoomLevel)-this.boardZoomLevel*mouseY(oldZoom);

            this.restrictPan();
        },
        panBoard(event) {
            this.boardOffset.x += event.movementX;
            this.boardOffset.y += event.movementY;
            this.restrictPan();
        },
        restrictPan() {
            if(Math.abs(this.boardOffset.x) > this.boardPanMargins.x) {
                this.boardOffset.x = Math.sign(this.boardOffset.x) * this.boardPanMargins.x;
            }
            if(Math.abs(this.boardOffset.y) > this.boardPanMargins.y) {
                this.boardOffset.y = Math.sign(this.boardOffset.y) * this.boardPanMargins.y;
            }
        }
    },
    mounted() {
        this.boardPanMargins.x = this.$refs.boardContainer.clientWidth / 4;
        this.boardPanMargins.y = this.$refs.boardContainer.clientHeight / 4;
    },
    beforeUpdate() {
        this.boardPanMargins.x = this.$refs.board.$el.clientWidth*this.boardZoomLevel/2 - this.$refs.boardContainer.clientWidth/4;
        const offsetY = this.$refs.board.$el.clientWidth * 0.0222; // offset from margin
        this.boardPanMargins.y = this.$refs.board.$el.clientHeight*this.boardZoomLevel/2 + offsetY - this.$refs.boardContainer.clientHeight/4;
    }
}
</script>

<style scoped>
.banner {
    font-size: 25px;
}

.gameScreen {
    display: inline-block;
    width: 60%;
    padding: 3%;
    border: 5px solid brown;
    overflow: hidden;
}

.playerScreen {
    display: inline-block;
    width: 70%;
}
</style>

