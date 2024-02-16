<script setup>
    import { doAction, getIcon } from '@/global';
import Building from './Building.vue';
</script>

<template>
    <div>
        <div class="popupText">
            <div class="popupAction" v-for="(action, i) in actions">
                <button class="popupButton" type="button" @click="performAction(i)"><Building :icon="getIcon(action.targetBuilding)" :scale="0.5"/></button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['actions'],
        emits: ['togglePopup'],
        methods: {
            async performAction(index) {
                this.$emit('togglePopup');
                await doAction(this.actions[index]);
            }
        }
    }
</script>

<style scoped>
.popupText {
    display: block;
    position: relative;
    z-index: 1;

    border-radius: 6px;
    background-color: white;
    width: 100%;

    text-align: center;
    color: black;
    font-size: 1.5vh;
}

.popupButton {
    position: relative;
    display: block;
    width: 100%;
    min-height: 30px;
}
</style>