<script setup>
    import { doAction, gameState } from '@/global';
</script>

<template>
    <div>
        <div class="popupText">{{ actions.length }}
            <div class="popupAction" v-for="(action, i) in actions">
                <button class="popupButton" type="button" @click="performAction(i)">Press!</button>
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

    height: fit-content;

    text-align: center;
    color: black;
    font-size: 1.5vh;
}
</style>