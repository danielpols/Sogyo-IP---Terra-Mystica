<script setup>
    import { doAction, getIcon } from '@/global';
import Building from './Building.vue';
</script>

<template>
    <div>
        <div class="popupText">
            <div class="popupAction" v-for="(action, i) in actions">
                <button class="popupButton" type="button" @click="performAction(i)">
                    <ul class="list">
                        <li>
                            <Building :icon="getIcon(action.targetBuilding)" :scale="1"/>
                        </li>
                        <li>
                            {{ action.cost[0] + "/" + action.cost[1] + "/" + action.cost[2] }}
                        </li>
                    </ul>
                </button>
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
    width: 150%;

    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    text-align: center;
    color: black;
    font-size: 1.5vh;
}

.popupButton {
    position: relative;
    display: block;
    margin: 0;
    padding: 0;
    width: 100%;
    min-height: 30px;
}

.list {
    position: relative;
    display: grid;
    margin: 0;
    padding: 0;
    list-style-type: none;
    grid-template-columns: 1fr 2fr;
    width: 100%;
    height: 100%;
}

.list li {
    position: relative;
    width: 100%;
    height: 100%;
}
</style>