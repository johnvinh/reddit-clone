<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let forumData = {};
    let message = "";

    onMount(async () => {
        const response = await fetch(`/api/forum/${data.forum.name}`)
        const text = await response.text();
        if (text === "") {
            message = "There is no forum with that name.";
        }
    });
</script>

{#if message}
    <p>{message}</p>
{/if}
{#if !message}
    <h1>r/{data.forum.name}</h1>
{/if}