# RESTful api

## Introduction

REST(REpresentational state transfer) is more a collection of principles than it is a set of standards. 
Other than its over-arching six constraints nothing is dictated. 
There are "best practices" and de-facto standards but those are
constantly evolving—with religious battles waging continuously.

## What is REST?

The REST architectural style describes six constraints. These constraints, applied to the architecture,
were originally communicated by Roy Fielding in his doctoral dissertation and defines the basis of
RESTful-style.

The six constraints are:

- Uniform Interface
- Stateless
- Cacheable
- Client-Server
- Layered System
- Code on Demand

### Uniform Interface

The uniform interface constraint defines the interface between clients and servers. It simplifies and
decouples the architecture, which enables each part to evolve independently. The four guiding
principles of the uniform interface are:

#### Resource-Based

Individual resources are identified in requests using URIs as resource identifiers. The resources
themselves are conceptually separate from the representations that are returned to the client. For
example, the server does not send its database, but rather, some HTML, XML or JSON that represents
some database records expressed, for instance, depending on the details of 
the request and the server implementation.

#### Manipulation of Resources Through Representations

When a client holds a representation of a resource, including any metadata attached, it has enough
information to modify or delete the resource on the server, provided it has permission to do so.

#### Self-descriptive Messages

Each message includes enough information to describe how to process the message. For example,
which parser to invoke may be specified by an Internet media type (previously known as a MIME
type). Responses also explicitly indicate their cache-ability.

#### Hypermedia as the Engine of Application State (HATEOAS)

Clients deliver state via body contents, query-string parameters, request headers and the requested URI
(the resource name). Services deliver state to clients via body content, response codes, and response
headers. This is technically referred-to as hypermedia (or hyperlinks within hypertext).
Aside from the description above, HATEOS also means that, where necessary, links are contained in
the returned body (or headers) to supply the URI for retrieval of the object itself or related objects.
We'll talk about this in more detail later.
The uniform interface that any REST services must provide is fundamental to its design.

### Stateless

As REST is an acronym for REpresentational State Transfer, statelessness is key. Essentially, what
this means is that the necessary state to handle the request is contained within the request itself,
whether as part of the URI, query-string parameters, body, or headers. The URI uniquely identifies the
resource and the body contains the state (or state change) of that resource. Then after the server does
it's processing, the appropriate state, or the piece(s) of state that matter, are communicated back to the
client via headers, status and response body.

### Cacheable

As on the World Wide Web, clients can cache responses. Responses must therefore, implicitly or
explicitly, define themselves as cacheable, or not, to prevent clients reusing stale or inappropriate data
in response to further requests. Well-managed caching partially or completely eliminates some client–
server interactions, further improving scalability and performance.

### Client–server

The uniform interface separates clients from servers. This separation of concerns means that, for
example, clients are not concerned with data storage, which remains internal to each server, so that the
portability of client code is improved. Servers are not concerned with the user interface or user state, so
that servers can be simpler and more scalable. Servers and clients may also be replaced and developed
independently, as long as the interface is not altered.

### Layered system

A client cannot ordinarily tell whether it is connected directly to the end server, or to an intermediary
along the way. Intermediary servers may improve system scalability by enabling load-balancing and by
providing shared caches. Layers may also enforce security policies.

### Code on demand (optional)

Servers are able to temporarily extend or customize the functionality of a client by transferring logic to
it that it can execute. Examples of this may include compiled components such as Java applets and
client-side scripts such as JavaScript.

Complying with these constraints, and thus conforming to the REST architectural style, will enable any
kind of distributed hypermedia system to have desirable emergent properties, such as performance,
scalability, simplicity, modifiability, visibility, portability and reliability.

NOTE: The only optional constraint of REST architecture is code on demand. If a service violates any
other constraint, it cannot strictly be referred to as RESTful.

## REST Quick Tips

Whether it's technically RESTful or not (according to the six constraints mentioned above), here are a
few recommended REST-like concepts that will result in better, more usable services:

### Use HTTP Verbs to Mean Something

Any API consumer is capable of sending GET, POST, PUT, and DELETE verbs, and they greatly
enhance the clarity of what a given request does. Also, GET requests must not change any underlying
resource data. Measurements and tracking may still occur, which updates data, but not resource data
identified by the URI.

### Sensible Resource Names

Having sensible resource names or paths (e.g., /posts/23 instead of /api?type=posts&id=23) improves
the clarity of what a given request does. Using URL query-string parameters is fantastic for filtering,
but not for resource names.

Appropriate resource names provide context for a service request, increasing understandability of the
service API. Resources are viewed hierarchically via their URI names, offering consumers a friendly,
easily-understood hierarchy of resources to leverage in their applications.

Resource names should be nouns—avoid verbs as resource names. It makes things more clear. Use the
HTTP methods to specify the verb portion of the request.

### XML and JSON

Favor JSON support as the default, but unless the costs of offering both JSON and XML are staggering,
offer them both. Ideally, let consumers switch between them by just changing an extension from .xml
to .json. In addition, for supporting AJAX-style user interfaces, a wrapped response is very helpful.
Provide a wrapped response, either by default or for separate extensions, such as .wjson and .wxml to
indicate the client is requesting a wrapped JSON or XML response (see Wrapped Responses below).

JSON in regards to a "standard" has very few requirements. And those requirements are only
syntactical in nature, not about content format or layout. In other words, the JSON response to a REST
service call is very much part of the contract—not described in a standard.

Regarding XML use in REST services, XML standards and conventions are really not in play other
than to utilize syntactically correct tags and text. In particular, namespaces are not, nor should they be
use in a RESTful service context. XML that is returned is more JSON like—simple and easy to read,
without the schema and namespace details present—just data and links. If it ends up being more
complex than this, see the first paragraph of this tip—the cost of XML will be staggering. In our
experience few consumers uses the XML responses anyway. This is the last 'nod' before it gets phased
out entirely.

### Create Fine-Grained Resources

When starting out, it's much easier to create APIs that mimic the underlying application domain or
database architecture of your system. Eventually, you'll want aggregate services—services that utilize
multiple underlying resources to reduce chattiness. But it's much easier to create larger resources later
from individual resources than it is to create fine-grained or individual resources from larger
aggregates. Make it easy on yourself and start with small, easily defined resources, providing CRUD
functionality on those. You can create those use-case-oriented, chattiness-reducing resources later.

### Consider Connectedness

One of the principles of REST is connectedness—via hypermedia links. While services are still useful
without them, APIs become more self-descriptive when links are returned in the response. At the very
least, a 'self' reference informs clients how the data was or can be retrieved. Additionally, utilize the
Location header to contain a link on resource creation via POST. For collections returned in
that support pagination, 'first', 'last', 'next' and 'prev' links at a minimum are very helpful.

## Definitions

### Idempotence

Contrary to how it sounds, make no mistake, this has no relation to certain areas of disfunction. From
Wikipedia:

        In computer science, the term idempotent is used more comprehensively to describe an
        operation that will produce the same results if executed once or multiple times. This may
        have a different meaning depending on the context in which it is applied. In the case of
        methods or subroutine calls with side effects, for instance, it means that the modified state
        remains the same after the first call.
        
From a RESTful service standpoint, for an operation (or service call) to be idempotent, clients can
make that same call repeatedly while producing the same result—operating much like a “setter”
method in a programming language. In other words, making multiple identical requests has the same
effect as making a single request. Note that while idempotent operations produce the same result on the
server (side effects), the response itself may not be the same (e.g. a resource's state may change
between requests).
The PUT and DELETE methods are defined to be idempotent. However, read the caveat on DELETE
in the HTTP Verbs, DELETE section below.
GET, HEAD, OPTIONS and TRACE methods are defined as idempotent also, since they are defined as
safe. Read the section on safety below.

### Safety

From Wikipedia:

        Some methods (for example, HEAD, GET, OPTIONS and TRACE) are defined as safe,
        which means they are intended only for information retrieval and should not change the
        state of the server. In other words, they should not have side effects, beyond relatively
        harmless effects such as logging, caching, the serving of banner advertisements or
        incrementing a web counter. Making arbitrary GET requests without regard to the context
        of the application's state should therefore be considered safe.
        
In short, safety means that calling the method does not cause side effects. Consequently, clients can
make safe requests repeatedly without worry of side effects on the server. This means that services
must adhere to the safety definitions of GET, HEAD, OPTIONS and TRACE operations. Otherwise,
besides being confusing to service consumers, it can cause problems for Web caching, search engines
and other automated agents—making unintended changes on the server.
By definition, safe operations are idempotent, since they produce the same result on the server.
Safe methods are implemented as read-only operations. However, safety does not mean that the server
must return the same response every time.

## HTTP Verbs

The HTTP verbs comprise a major portion of our “uniform interface” constraint and provide us the
action counterpart to the noun-based resource. The primary or most-commonly-used HTTP verbs (or
methods, as they are properly called) are POST, GET, PUT, and DELETE. These correspond to create,
read, update, and delete (or CRUD) operations, respectively. There are a number of other verbs, too,
but are utilized less frequently. Of those less-frequent methods, OPTIONS and HEAD are used more
often than others.

Check for HTTP requsets/response examples in [README](../README.md).