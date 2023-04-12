/** @type {import('./$types').PageLoad} */
export async function load({params}) {
    return {
        forum: {
            name: params.forum
        }
    }
}