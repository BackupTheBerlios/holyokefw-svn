This is a modified version of jserial-0.22.  Two changes are made:

1. Serialize/deserialize to streams instead of buffers.  Buffers have the
problem of buffer overflow/underflows happening.

2. Allow objects of certain types to be NOT saved for later referencing.  This
is essential for timseries serialization.
