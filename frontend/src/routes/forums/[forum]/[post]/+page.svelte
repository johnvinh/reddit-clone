<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let message;
    let title = "";
    let textualContent = "";

    onMount(async () => {
        const response = await fetch(`/api/post/${data.post.id}`,
            {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });

        const text = await response.text();

        if (text === "") {
            message = "That post doesn't exist.";
            return;
        }
        const json = JSON.parse(text);
        console.log(json);
        title = json["title"];
        textualContent = json["textualContent"];
        console.log(textualContent);
    });
</script>

<h2>{title}</h2>
{#if message}
    <p>{message}</p>
{/if}
{#if !message}
    <p>{textualContent}</p>
{/if}