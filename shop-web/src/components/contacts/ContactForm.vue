<template>

    <Toast />
    <div class="flex flex-col   gap-10">
        <div class="w-md">
        <p class="text-2xl">Contact us for any questions or inquiries.</p>
    </div>
  <div>
<Form  :initialValues :resolver    @submit="onFormSubmit" class="flex flex-col gap-4 w-full sm:w-56">
    <FormField v-slot="$field" name="email" initialValue=""  class="flex flex-col gap-1">
                <InputText type="text" placeholder="Email" />
                <Message v-if="$field?.invalid" severity="error" size="small" variant="simple">{{ $field.error?.message }}</Message>
    </FormField>
    <FormField v-slot="$field" name="details" class="flex flex-col gap-1">
                <Textarea placeholder="Message" rows="5" cols="30" />
                <Message v-if="$field?.invalid" severity="error" size="small" variant="simple">{{ $field.error?.message }}</Message>
    </FormField>
    <Button type="submit"  class="p-button-primary"   label="Submit" />
</Form>
</div>
    </div>
    
</template>

<script setup lang="ts">
import { Form } from '@primevue/forms';
import { zodResolver } from '@primevue/forms/resolvers/zod';
import { reactive, ref } from 'vue';
import { FormField } from '@primevue/forms';
import { z } from 'zod';
import { useToast } from 'primevue/usetoast';
import Toast from 'primevue/toast';
import { Button } from 'primevue';
import InputText from 'primevue/inputtext';
import Message from 'primevue/message';
import Textarea from 'primevue/textarea';

const toast = useToast();

const initialValues = reactive({
    details: '',
    email: ''
});

 
const resolver = ref(zodResolver(
    z.object({
        details: z.string().min(1, { message: 'Message is mandatory' }).max(300, { message: 'Max length is 300' }),
        email: z.string().email({ message: 'Invalid email.' })
    })
));

const onFormSubmit = ({ valid }: { valid: boolean })  => {
  if (valid) {
    toast.add({ severity: 'success', summary: 'Demande de contact envoyée avec succès', life: 3000 });
  }
};
</script>