<script setup>
    import None from './buildings/None.vue';
    import Dwelling from './buildings/Dwelling.vue';
    import TradingCity from './buildings/TradingCity.vue';
    import Stronghold from './buildings/Stronghold.vue';
    import Temple from './buildings/Temple.vue';
    import Sanctuary from './buildings/Sanctuary.vue';
</script>

<template>
    <component :is="icon" class="building"/>
</template>

<script>
    export default {
        props: ['icon', 'scale'],
        components: {
            None,
            Dwelling,
            TradingCity,
            Stronghold,
            Temple,
            Sanctuary
        }
    }
</script>

<style scoped>

.building {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%) scale(v-bind(scale));
    width: 100%;
    height: 100%;
}
</style>
