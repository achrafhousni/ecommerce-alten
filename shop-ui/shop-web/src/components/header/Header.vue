<template>
    <!--Im lookung for addresse to get a secure logo from vue 3 HERE :-->
    
     
     <header class="fixed w-full top-0 left-0 z-50 shadow-md bg-white h-16 border-b">
        <div class="flex items-center justify-between px-6 h-full">
            <div class="flex items-center gap-4">
                <img src="https://vuejs.org/images/logo.png" alt="Vue.js Logo" class="h-12" />
                <h1 class="text-xl font-bold text-gray-800">Alten</h1>
            </div>
            <div class="flex items-center gap-4">
                 
                <RouterLink :to="{ name: 'cart' }" class="relative cursor-pointer">
                    <i class="pi pi-shopping-cart text-2xl" style="font-size: 2rem"></i>
                    <span v-if="cartItems > 0" class="absolute -top-2 -right-2 bg-red-500 text-white text-xs rounded-full px-2 py-1">
                        {{ cartItems }}
                    </span>
                </RouterLink>

                 
            </div>
        </div>
         <div class="flex items-center gap-4">   
            
            
        </div>
    </header>


</template>

<script setup lang="ts">
import { useCartStore } from '@/stores/cartStore';
import { Button } from 'primevue';
import { computed, ref } from 'vue';
import { RouterLink } from 'vue-router';

 // afficher  le nombre d items ici pour le badge
    
    const cartStore = useCartStore();
    const cartItems = computed(() => cartStore.items.length);

    console.log("cart items :",cartItems.value);

</script>