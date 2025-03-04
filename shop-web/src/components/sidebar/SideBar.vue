<template>
    <aside class="w-64 h-full bg-white p-4 border-r shadow-md fixed left-0 top-16">
 
        <Menu :model="menuItems" class="p-0" />
    </aside>
</template>
<script setup lang="ts">

import { ref } from "vue";
import Menu from "primevue/menu";
import router from "@/router";

const menuItems = ref([
    { label: "Produits", icon: "pi pi-home", command: () => router.push("/") },
    { label: "Panier", icon: "pi pi-shopping-cart",command: () => router.push("/cart") },
    { label: "Contact", icon: "pi pi-envelope", command: () => router.push("/contact") },
]);

</script>