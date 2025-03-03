<template>
    <div v-if="cartItems.length === 0" class="text-gray-500">
      
       <div class="w-3xs mx-auto text-center">
           <p class="text-2xl">Your cart is empty.</p>
       </div>
      
    
       
   </div>
   
       <div   v-else>
   <DataView :data-key="'product'" :value="cartItems" paginator :rows="5">
       <template #list="slotProps">
           <div class="flex flex-col">
               <div v-for="(item, index) in slotProps.items" :key="index">
                   <div class="flex flex-col sm:flex-row sm:items-center p-6 gap-4" :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
                       <div class="md:w-40 relative">
                           <img class="block xl:block mx-auto rounded w-full" :src="`https://primefaces.org/cdn/primevue/images/product/${item.product.image}`" :alt="item.product.name" />
                           <div class="absolute bg-black/70 rounded-border" style="left: 4px; top: 4px">
                               <Tag :value="item.product.inventoryStatus" :severity="getSeverity(item.product)"></Tag> 
                           </div>
                       </div>
                       <div class="flex flex-col md:flex-row justify-between md:items-center flex-1 gap-6">
                           <div class="flex flex-row md:flex-col justify-between items-start gap-2">
                               <div>
                                   <span class="font-medium text-surface-500 dark:text-surface-400 text-sm">{{ item.product.category }}</span>
                                   <div class="text-lg font-medium mt-2">{{ item.product.name }}</div>
                               </div>
                               <div class="bg-surface-100 p-1" style="border-radius: 30px">
                                   <div class="bg-surface-0 flex items-center gap-2 justify-center py-1 px-2" style="border-radius: 30px; box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.04), 0px 1px 2px 0px rgba(0, 0, 0, 0.06)">
                                       <span class="text-surface-900 font-medium text-sm">{{ item.product.rating }}</span>
                                       <i class="pi pi-star-fill text-yellow-500"></i>
                                   </div>
                               </div>
                           </div>
                           <div class="flex flex-col md:items-end gap-8">
                               <span class="text-xl font-semibold">${{ item.product.price }}</span>
                               <div class="flex gap-2">
                                   <div class="flex flex-row-reverse md:flex-row gap-2">
                                    <Button icon="pi pi-trash" size="small" label="Remove" severity="danger"
                                           @click="removeFromCart(item.product.id)"></Button>
                                </div>
                                  <!--- <Button icon="pi pi-trash" size="small" label="Remove from Cart" severity="danger"
                                           @click="removeFromCart(item.product.id)"></Button>-->
                                 </div>
                           </div>
                       </div>
                   </div>
               </div>
           </div>
       </template>
   </DataView>
   </div>
   </template>
   
   <script setup lang="ts">
   
   
   import { computed, ref } from 'vue';
   import DataView  from 'primevue/dataview';
   import  Button from 'primevue/button';
   import  Tag from 'primevue/tag';
   import type { Product } from '@/types/Product';
   import { useCartStore } from '@/stores/cartStore';
    
   const cartStore = useCartStore();
   const isOutlined = ref(true);
   
    
   const cartItems = computed(() => cartStore.items);
   const removeFromCart = (productId: number) => {
     cartStore.removeProduct(productId);
   };
   
   const getSeverity = (product:Product):string => {
       switch (product.inventoryStatus) {
           case 'INSTOCK':
               return 'success';
   
           case 'LOWSTOCK':
               return 'warn';
   
           case 'OUTOFSTOCK':
               return 'danger';
   
           default:
               return 'success';
       }
   }
   </script>